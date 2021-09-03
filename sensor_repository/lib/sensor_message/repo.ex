defmodule SensorMessage.Repo do
  use Ecto.Repo,
    otp_app: :sensor_repository,
    adapter: Ecto.Adapters.Postgres
end
