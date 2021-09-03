defmodule SensorMessage.Repo.CreateStringMessageTest do
  use ExUnit.Case
  import SensorMessage.StringMessage

  @valid_message %SensorMessage.StringMessage{created_at: DateTime.utc_now(), sensor_id: "sensor_id", value: "on"}
  @invalid_message %SensorMessage.StringMessage{}
  @message_wrong_types %SensorMessage.StringMessage{created_at: "test", sensor_id: 2, value: 2.0}

  setup do
    :ok = Ecto.Adapters.SQL.Sandbox.checkout(SensorMessage.Repo)
  end

  test "Store string message successfully" do
    {:ok, _} = @valid_message |> changeset |> SensorMessage.Repo.insert

    actual = SensorMessage.StringMessage
      |> SensorMessage.Repo.get_by(sensor_id: @valid_message.sensor_id, created_at: @valid_message.created_at)

    assert actual.created_at == @valid_message.created_at
    assert actual.sensor_id == @valid_message.sensor_id
    assert actual.value == @valid_message.value
  end

  test "Store string message duplicated" do
    {:ok, _} = @valid_message |> changeset |> SensorMessage.Repo.insert

    assert_raise Ecto.ConstraintError, fn -> @valid_message |> changeset |> SensorMessage.Repo.insert end
  end

  test "Store string message empty fields" do
    {:error, 
      %{errors: [
        sensor_id: {"can't be blank", _},
        value: {"can't be blank", _},
        created_at: {"can't be blank", _}
      ]}
    } = @invalid_message |> changeset |> SensorMessage.Repo.insert
  end

  test "Store string message fields with wrong type" do
    assert_raise Ecto.ChangeError, fn -> @message_wrong_types |> changeset |> SensorMessage.Repo.insert end
  end

end
