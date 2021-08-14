defmodule MyBroadwayTest do
  use ExUnit.Case, async: true

  @numerical_sensor_message "{\"event\": \"sensor_message\", \"sensor_id\": \"mock_sensor_1\", \"timestamp\": \"2021-08-14T16:22:59.759941\", \"value_type\": \"numerical\", \"value\": 2}"
  @string_sensor_message "{\"event\": \"sensor_message\", \"sensor_id\": \"mock_sensor_1\", \"timestamp\": \"2021-08-14T16:22:59.759941\", \"value_type\": \"numerical\", \"value\": \"on\"}"

  test "receive numerical sensor message successfully" do
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, @numerical_sensor_message)
    assert_receive {:ack, ^ref, [%{data: @numerical_sensor_message}], []}
  end

  test "receive string sensor message successfully" do
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, @string_sensor_message)
    assert_receive {:ack, ^ref, [%{data: @string_sensor_message}], []}
  end
end
