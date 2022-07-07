#include "crt_debug.h"
#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <format>
using namespace std;

struct ResultSet{

    ResultSet() :p{ NEW int }{}
    ResultSet(const string& msg):p{NEW int}, msg{msg}{}
    void print() { 
        cout << format("msg: {}\n", msg);
    }
    void Close() { delete p; p = nullptr; }
    string msg{};
    
private:
    int* p{};
};
struct Statement{
    ResultSet* query(const string& msg) {
        return NEW ResultSet(msg);
    }
    Statement():p(NEW int){}
    void CloseResultSet(ResultSet*& r) const { r->Close(); delete r; r = nullptr; }
    void Close() { delete p; p = nullptr; }
private:
    int* p{};
};
struct Connection {
    Statement* createStatement() {
        return NEW Statement();
    }
    Connection():p(NEW int){}
    void CloseStatement(Statement*& s) const { s->Close(); delete s; s = nullptr; }
    void Close() { delete p; p = nullptr; }
private:
    int* p{};
};
struct Environment {
    static Environment* create() {
        return NEW Environment();
    }
    Connection* createConnection() {
        return NEW Connection();
    }
    Environment():p(NEW int){}
    void Close() { delete p; p = nullptr; }
    static void Close(Environment* env) {
        env->Close();
        delete env;
    }
    void CloseConnection(Connection*& c) const{
        c->Close();
        delete c;
        c = nullptr;
    }
private:
    int* p{};
};
struct EnvironmentDeleter {
    void operator()(Environment* e) const {
        Environment::Close(e);
    }
};
struct ConnectionDeleter {
    ConnectionDeleter(shared_ptr<Environment> e):env{e}{}
    void operator()(Connection*& conn) const {
        env->CloseConnection(conn);
    }
private:
    shared_ptr<Environment> env;
};

struct StatementDeleter {
    StatementDeleter(shared_ptr<Connection> conn):conn{conn}{};
    void operator()(Statement*& stmt) const { conn->CloseStatement(stmt); }
private:
    shared_ptr<Connection> conn;
};
class ResultSetDeleter {
public:
    ResultSetDeleter(shared_ptr<Statement> stmt) :stmt{stmt}{}
    void operator()(ResultSet* rs) const { stmt->CloseResultSet(rs); }

    ResultSetDeleter(shared_ptr<Statement> stmt, const string& name) :stmt{stmt}, name{name}{}
    ~ResultSetDeleter() {
        cout << format("ResultSetDeleter({})->stmt.use_count: {}\n", name, stmt.use_count());
    }
private:
    shared_ptr<Statement> stmt{};
    const string name{};
};
class DbManager {
public:
    DbManager()
        : env{Environment::create(), EnvironmentDeleter()},
          conn{env->createConnection(), ConnectionDeleter(env)}
    {}
    shared_ptr<ResultSet> Quiz2(const string& msg) {
        shared_ptr<Statement> stmt{ conn->createStatement(), StatementDeleter(conn) };
        return { stmt->query(msg), ResultSetDeleter(stmt, msg) };
    }

    unique_ptr<ResultSet, ResultSetDeleter> Quiz(const string& msg) {
        shared_ptr<Statement> stmt{ conn->createStatement(), StatementDeleter(conn) };
        return { stmt->query(msg), ResultSetDeleter(stmt, msg) };
    }

private:
    shared_ptr<Environment> env{nullptr};
    shared_ptr<Connection> conn{nullptr};
};

TEST(ResourceRescue, DmUsage) {
    DbManager dm;

    const string sql = "test1";
    shared_ptr<ResultSet> rs1{ dm.Quiz2(sql) };
    const string sql2{ "test22222" };
    shared_ptr<ResultSet> rs2{ dm.Quiz2(sql2) };
    auto rs3{ rs2 };

    ASSERT_EQ(sql, rs1->msg);
    ASSERT_EQ(sql2, rs2->msg);
    ASSERT_EQ(sql2, rs3->msg);

    unique_ptr<ResultSet, ResultSetDeleter> rs4{ dm.Quiz(sql) };
    ASSERT_EQ(sql, rs4->msg);

    unique_ptr<ResultSet, ResultSetDeleter> rs5{ dm.Quiz(sql2) };
    ASSERT_EQ(sql2, rs5->msg);
}

TEST(ResourceRescue, shared) {
    ResultSet rs;
    rs.Close();
    Environment env;
    env.Close();
    shared_ptr<Environment> env1{ Environment::create(), EnvironmentDeleter() };
    shared_ptr<Environment> env2{ NEW Environment(), EnvironmentDeleter() };
    cout << env2.get() << " " << env2.use_count() << endl;
    auto env3{ env2 };
    cout << env3.get() << " " << env3.use_count() << endl;

    shared_ptr<Connection> conn{ env1->createConnection(), ConnectionDeleter(env1) };
    shared_ptr<Statement> stmt{ conn->createStatement(), StatementDeleter{conn} };
    const string sql = "test1";
    shared_ptr<ResultSet> rs2{ stmt->query(sql), ResultSetDeleter(stmt) };
    ASSERT_EQ(sql, rs2->msg);
    cout << format("rs2 use count: {}\n", rs2.use_count());
    unique_ptr<ResultSet, ResultSetDeleter> rs3{ stmt->query(sql), ResultSetDeleter(stmt) };
    ASSERT_EQ(sql, rs3->msg);
}

TEST(ResourceRescue, create) {
    Environment* env1  = Environment::create();
    Environment::Close(env1);
    Environment* env  = Environment::create();
    Connection* conn = env->createConnection();
    auto stmt = conn->createStatement();

    auto msg = "test111"s;
    auto rs = stmt->query(msg);
    ASSERT_EQ(msg, rs->msg);
    stmt->CloseResultSet(rs);

    auto msg2 = "test222"s;
    auto rs2 = stmt->query(msg2);
    ASSERT_EQ(msg2, rs2->msg);
    stmt->CloseResultSet(rs2);

    conn->CloseStatement(stmt);
    env->CloseConnection(conn);
    ASSERT_EQ(conn, nullptr);
    env->Close();
    delete env;
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
#if _DEBUG
    _CrtMemState memoryState { 0 };
    _CrtMemCheckpoint(&memoryState);
#endif
    auto res = RUN_ALL_TESTS();
#if _DEBUG
    _CrtMemDumpAllObjectsSince(&memoryState);
#endif
    return res;
}
