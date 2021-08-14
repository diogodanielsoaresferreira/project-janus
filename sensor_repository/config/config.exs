import Config
config :sensor_repository,
  producer_module: BroadwayRabbitMQ.Producer,
  producer_host: "localhost"

import_config "#{config_env()}.exs"