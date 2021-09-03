defmodule SensorMessage.Repo.CreateNumericMessageTest do
  use ExUnit.Case
  import SensorMessage.NumericMessage

  @valid_message %SensorMessage.NumericMessage{created_at: DateTime.utc_now(), sensor_id: "sensor_id", value: 2.0}
  @invalid_message %SensorMessage.NumericMessage{}
  @message_wrong_types %SensorMessage.NumericMessage{created_at: "test", sensor_id: 2, value: "2.0"}

  setup do
    :ok = Ecto.Adapters.SQL.Sandbox.checkout(SensorMessage.Repo)
  end

  test "Store numeric message successfully" do
  	{:ok, _} = @valid_message |> changeset |> SensorMessage.Repo.insert

  	actual = SensorMessage.NumericMessage
  	  |> SensorMessage.Repo.get_by(sensor_id: @valid_message.sensor_id, created_at: @valid_message.created_at)

  	assert actual.created_at == @valid_message.created_at
  	assert actual.sensor_id == @valid_message.sensor_id
  	assert actual.value == @valid_message.value
  end

  test "Store numeric message duplicated" do
  	{:ok, _} = @valid_message |> changeset |> SensorMessage.Repo.insert

  	assert_raise Ecto.ConstraintError, fn -> @valid_message |> changeset |> SensorMessage.Repo.insert end
  end

  test "Store numeric message empty fields" do
  	{:error, 
  	  %{errors: [
  	    sensor_id: {"can't be blank", _},
        value: {"can't be blank", _},
        created_at: {"can't be blank", _}
      ]}
    } = @invalid_message |> changeset |> SensorMessage.Repo.insert
  end

  test "Store numeric message fields with wrong type" do
  	assert_raise Ecto.ChangeError, fn -> @message_wrong_types |> changeset |> SensorMessage.Repo.insert end
  end

end
