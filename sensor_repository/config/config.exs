import Config

config :sensor_repository,
  producer_module: BroadwayRabbitMQ.Producer,
  producer_host: "localhost"

config :sensor_repository, ecto_repos: [SensorMessage.Repo]

config :sensor_repository, SensorMessage.Repo,
  database: "sensor_repository_repo",
  username: "postgres",
  password: "password",
  hostname: "localhost"

import_config "#{config_env()}.exs"
