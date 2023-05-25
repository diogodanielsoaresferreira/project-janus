defmodule SensorRepository.SensorMessagesConsumerTest do
  # TODO Configure Broadway to run async tests
  use ExUnit.Case, async: false

  @sensor_id "sensor_id"
  @timestamp "2023-05-24T21:34:00.891210+00:00"
  
  @numerical_sensor_message %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "numerical", "value" => 6.87}
  @numerical_sensor_message_zero_value %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "numerical", "value" => 0}
  @numerical_sensor_message_negative_value %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "numerical", "value" => -2.47}
  @numerical_sensor_message_wrong_timestamp %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "2023-05-24T21:34:00.891210", "value_type" => "numerical", "value" => 6.87}
  @numerical_sensor_message_wrong_value %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "numerical", "value" => "on"}

  @string_sensor_message %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "string", "value" => "on"}
  @string_sensor_message_wrong_timestamp %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "2023-05-24T21:34:00.891210", "value_type" => "string", "value" => "on"}
  @string_sensor_message_wrong_value %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "string", "value" => 6.87}

  @unknown_type_sensor_message %{"event" => "sensor_message", "sensor_id" => "#{@sensor_id}", "timestamp" => "#{@timestamp}", "value_type" => "unknown", "value" => "anything"}

  setup do
    :ok = Ecto.Adapters.SQL.Sandbox.checkout(SensorMessage.Repo)
    Ecto.Adapters.SQL.Sandbox.mode(SensorMessage.Repo, {:shared, self()})
  end

  test "receive and store numerical sensor message successfully" do
    message_encoded = Poison.encode!(@numerical_sensor_message)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [%{data: ^message_encoded}], []}
    SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
  end

  test "receive and store numerical sensor message successfully with zero value" do
    message_encoded = Poison.encode!(@numerical_sensor_message_zero_value)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [%{data: ^message_encoded}], []}
    SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
  end

  test "receive and store numerical sensor message successfully with negative value" do
    message_encoded = Poison.encode!(@numerical_sensor_message_negative_value)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [%{data: ^message_encoded}], []}
    SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
  end

  test "not receive and not store numerical message with wrong timestamp format" do
    message_encoded = Poison.encode!(@numerical_sensor_message_wrong_timestamp)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [], [%{data: ^message_encoded}]}
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
  end

  test "not receive and not store numerical message with wrong value type" do
    message_encoded = Poison.encode!(@numerical_sensor_message_wrong_value)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [], [%{data: ^message_encoded}]}
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
  end

  test "not receive and not store duplicated message" do
    message_encoded = Poison.encode!(@numerical_sensor_message)
    ref = Broadway.test_batch(SensorRepository.SensorMessagesConsumer, [message_encoded, message_encoded])
    assert_receive {:ack, ^ref, [%{data: ^message_encoded}], [%{data: ^message_encoded}]}
    SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
  end

  test "receive and store string sensor message successfully" do
    message_encoded = Poison.encode!(@string_sensor_message)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [%{data: ^message_encoded}], []}
    SensorMessage.StringMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
  end

  test "not receive and not store string message with wrong timestamp format" do
    message_encoded = Poison.encode!(@string_sensor_message_wrong_timestamp)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [], [%{data: ^message_encoded}]}
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.StringMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
  end

  test "not receive and not store string message with wrong value type" do
    message_encoded = Poison.encode!(@string_sensor_message_wrong_value)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [], [%{data: ^message_encoded}]}
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.StringMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
  end

  test "not receive and not store message with unknown type" do
    message_encoded = Poison.encode!(@unknown_type_sensor_message)
    ref = Broadway.test_message(SensorRepository.SensorMessagesConsumer, message_encoded)
    assert_receive {:ack, ^ref, [], [%{data: ^message_encoded}]}
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.NumericMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
    assert_raise Ecto.NoResultsError, fn -> SensorMessage.StringMessage
      |> SensorMessage.Repo.get_by!(sensor_id: @sensor_id, created_at: @timestamp)
    end
  end

end
