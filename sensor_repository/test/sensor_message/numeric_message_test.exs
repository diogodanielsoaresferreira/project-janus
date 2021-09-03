defmodule SensorRepository.NumericMessageTest do
  use ExUnit.Case, async: true
  import SensorMessage.NumericMessage

  @valid_message %SensorMessage.NumericMessage{created_at: DateTime.now!("Etc/UTC"), sensor_id: "sensor_id", value: 23.4}
  @message_without_created_at %SensorMessage.NumericMessage{sensor_id: "sensor_id", value: 23.4}
  @message_without_sensor_id %SensorMessage.NumericMessage{created_at: DateTime.now!("Etc/UTC"), value: 23.4}
  @message_without_value %SensorMessage.NumericMessage{created_at: DateTime.now!("Etc/UTC"), sensor_id: "sensor_id"}


  test "create correct numeric message" do
    changeset = @valid_message |> changeset
    assert changeset.valid?
  end

  test "create numeric message without created_at" do
    changeset = @message_without_created_at |> changeset
    assert !changeset.valid?
  end

  test "create numeric message without sensor_id" do
    changeset = @message_without_sensor_id |> changeset
    assert !changeset.valid?
  end

  test "create numeric message without value" do
    changeset = @message_without_value |> changeset
    assert !changeset.valid?
  end

end
