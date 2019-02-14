#!/usr/bin/python3

from unittest import TestCase, main
from time import sleep, time
from Worker import Worker, ClosableQueue

class WorkerTestCase(TestCase):
    
    def test_do(self):
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
        shouldBe = count * 0.3 + 0.1
        usage = end - start
        self.assertAlmostEqual(shouldBe, usage, delta=0.5)
        print("should %g secs, Took %g secs" % (shouldBe, usage))
    
def trigger(q, count):
    for _ in range(count):
        q.put(object())

def finish(queues):
    for q in queues[:-1]:
        q.close()
        q.join()
def bar(item):
    sleep(0.3)
    print("bar end")

def foo(item):
    sleep(0.1)
    print("foo end")
 
if __name__ == '__main__':
    main()
