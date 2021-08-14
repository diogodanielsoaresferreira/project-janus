import Config
config :sensor_repository,
  producer_host: System.get_env("RABBITMQ_HOST")
