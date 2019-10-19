import threading
import time
import logging
import concurrent.futures

format = "%(asctime)s: %(message)s"
logging.basicConfig(format=format, level=logging.INFO, datefmt="%H:%M:%S")


class Counter:
    def __init__(self):
        self.value = 0
        self._lock = threading.Lock()

    def increment(self, thread_name):
        # logging.info("Thread {} incremets value".format(thread_name))
        with self._lock:
            self.value += 1
        # logging.info("Thread {} has incremented value".format(thread_name))

    def get_value(self):
        with self._lock:
            return self.value


def run_counter(n_threads):
    COUNTER_VALUE = 1000000

    counter_1 = Counter()
    a = time.time()

    with concurrent.futures.ThreadPoolExecutor() as executor:
        while executor.submit(counter_1.get_value).result() < COUNTER_VALUE:
            threads = range(n_threads)
            for t in threads:
                executor.submit(counter_1.increment, t)

    b = time.time()
    return b - a
#     print('Result: ', counter_1.get_value())
#     print('Time taken: ', b - a)

    # print('second experiment')
    # counter_2 = Counter()
    # a = time.time()
    # for i in range(500):
    #     counter_2.increment(0)
    # b = time.time()
    # print('Time taken: ', b - a)



