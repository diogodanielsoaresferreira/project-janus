defmodule SensorMessage.Repo.Migrations.CreateNumericMessage do
  use Ecto.Migration

  def up do
    create table(:numeric_message, primary_key: false) do
      add :created_at, :utc_datetime_usec, primary_key: true
      add :sensor_id, :string, primary_key: true
      add :value, :float, null: false
    end

    execute("SELECT create_hypertable('numeric_message', 'created_at')")
  end

  def down do
    drop(table(:numeric_message))
  end
end
