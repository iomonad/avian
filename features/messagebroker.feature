Feature: Message Broker (RabbitMQ)
  To process pivot url and avoid
  queue overflow, an mq will be
  implented on top of Akka Stream.

    Scenario: Using RabbitMQ to process target pivots
    Given I send a pivot to MQ
    When I found a node by scraper actor
    Then The queue pivot should be passed to actor `receive` method
