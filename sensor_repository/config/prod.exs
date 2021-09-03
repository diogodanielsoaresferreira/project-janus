import Config

config :sensor_repository,
  producer_host: System.get_env("RABBITMQ_HOST")

config :sensor_repository, SensorMessage.Repo,
  hostname: System.get_env("DB_HOST")

