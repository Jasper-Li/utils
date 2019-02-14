#!/usr/bin/python3
from Worker import Worker, ClosableQueue
from time import sleep, time
def trigger(q, count):
    for _ in range(count):
        q.put(object())

def finish(queues):
    for q in queues[:-1]:
        q.close()
        q.join()

def main():
    in_queue = ClosableQueue()
    out_queue = ClosableQueue()
    out2_queue = ClosableQueue()

    queues = [
        in_queue,
        out_queue,
        out2_queue,
    ]

    threads = [
        Worker(foo, in_queue, out_queue),
        Worker(bar, out_queue, out2_queue),
    ]

    for t in threads:
        t.start()
    count = 10  
    start=time()
    
    trigger(queues[0], count)

    finish(queues)

    end=time()
    print("should %g secs, Took %g secs" % (count * 0.3 + 0.1, end - start))

def bar(item):
    sleep(0.3)
    print("bar end")

def foo(item):
    sleep(0.1)
    print("foo end")
 

if __name__ == '__main__':
    main()
