defmodule SensorMessage.StringMessage do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "string_message" do
    field :created_at, :utc_datetime_usec, primary_key: true
    field :sensor_id, :string, primary_key: true
    field :value, :string
  end

  def changeset(message, params \\ %{}) do
    message
    |> cast(params, [:sensor_id, :value, :created_at])
    |> validate_required([:sensor_id, :value, :created_at])
  end
end
