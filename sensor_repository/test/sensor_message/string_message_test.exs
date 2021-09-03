defmodule SensorRepository.StringMessageTest do
  use ExUnit.Case, async: true
  import SensorMessage.StringMessage

  @valid_message %SensorMessage.StringMessage{created_at: DateTime.now!("Etc/UTC"), sensor_id: "sensor_id", value: "on"}
  @message_without_created_at %SensorMessage.StringMessage{sensor_id: "sensor_id", value: "on"}
  @message_without_sensor_id %SensorMessage.StringMessage{created_at: DateTime.now!("Etc/UTC"), value: "on"}
  @message_without_value %SensorMessage.StringMessage{created_at: DateTime.now!("Etc/UTC"), sensor_id: "sensor_id"}


  test "create correct string message" do
    changeset = @valid_message |> changeset
    assert changeset.valid?
  end

  test "create string message without created_at" do
    changeset = @message_without_created_at |> changeset
    assert !changeset.valid?
  end

  test "create string message without sensor_id" do
    changeset = @message_without_sensor_id |> changeset
    assert !changeset.valid?
  end

  test "create string message without value" do
    changeset = @message_without_value |> changeset
    assert !changeset.valid?
  end

end
