package hello.springtx.order

class NotEnoughMoneyException(
    override val message: String
) : Exception(message)