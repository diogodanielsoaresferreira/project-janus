defmodule SensorMessage.Repo.Migrations.CreateStringMessage do
  use Ecto.Migration

  def up do
    create table(:string_message, primary_key: false) do
      add :created_at, :utc_datetime_usec, primary_key: true
      add :sensor_id, :string, primary_key: true
      add :value, :string, null: false
    end

    execute("SELECT create_hypertable('string_message', 'created_at')")
  end

  def down do
    drop(table(:string_message))
  end
end
