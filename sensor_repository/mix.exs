defmodule SensorRepository.MixProject do
  use Mix.Project

  def project do
    [
      app: :sensor_repository,
      version: "0.1.0",
      elixir: "~> 1.12",
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      aliases: aliases()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:lager, :logger, :amqp],
      mod: {SensorRepository.Application, []}
    ]
  end

  defp aliases do
    [
      test: ["ecto.drop", "ecto.create", "ecto.migrate", "test"]
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:broadway_rabbitmq, "~> 0.6.0"},
      {:ecto_sql, "~> 3.7.0"},
      {:postgrex, ">= 0.15.0"}
    ]
  end
end
