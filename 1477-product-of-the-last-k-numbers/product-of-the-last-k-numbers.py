class ProductOfNumbers:

    def __init__(self):
        # Prefix products since the most recent zero.
        self.prefix = [1]

    def add(self, num: int) -> None:
        if num == 0:
            # A query spanning this zero has product 0.
            self.prefix = [1]
        else:
            self.prefix.append(self.prefix[-1] * num)

    def getProduct(self, k: int) -> int:
        if k >= len(self.prefix):
            return 0  # The last k numbers include a zero.
        return self.prefix[-1] // self.prefix[-k - 1]