#include "CLI/CLI.hpp"
import<iostream>;
import BqfsConverterLib;

int main(int argc, const char* argv[])
{
    CLI::App app{ "Ti bqfs converter." };
    std::string input, output;
    app.add_option("-i", input, "input file: bqfs .fs file.");
    app.add_option("-o", output, "output file: .h file");
	CLI11_PARSE(app, argc, argv);

    const BqfsConverterLib bq{ input };
    bq.write_to(output);
}

