defmodule SensorRepository.MixProject do
  use Mix.Project

  def project do
    [
      app: :sensor_repository,
      version: "0.1.0",
      elixir: "~> 1.12",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:lager, :logger, :amqp],
      mod: {SensorRepository.Application, []}
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:broadway_rabbitmq, "~> 0.6.0"},
    ]
  end
end
