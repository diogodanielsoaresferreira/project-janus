import Config

config :sensor_repository,
  producer_module: Broadway.DummyProducer

config :sensor_repository, SensorMessage.Repo,
  database: "sensor_repository_repo_testing",
  pool: Ecto.Adapters.SQL.Sandbox
