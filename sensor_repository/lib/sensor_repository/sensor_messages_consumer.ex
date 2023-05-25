defmodule SensorRepository.SensorMessagesConsumer do
  use Broadway
  import SensorMessage.NumericMessage
  import SensorMessage.StringMessage

  def start_link(_opts) do

    Broadway.start_link(__MODULE__,
      name: __MODULE__,
      producer: [
        module: {
          Application.fetch_env!(:sensor_repository, :producer_module),
          connection: [
            host: Application.fetch_env!(:sensor_repository, :producer_host)
          ],
          queue: "sensor_message_repository_receiver",
          qos: [
            prefetch_count: 10,
          ],
          on_failure: :reject
        },
        concurrency: 1
      ],
      processors: [
        default: [
          concurrency: 10
        ]
      ]
    )
  end

  @impl true
  def handle_message(_, message, _) do
    message_parsed = Poison.decode!(message.data)
    changesett = case message_parsed["value_type"] do
      "numerical" -> mapToNumericSensorMessage(message_parsed) |> SensorMessage.NumericMessage.changeset
      "string" -> mapToStringSensorMessage(message_parsed) |> SensorMessage.StringMessage.changeset
      _ -> raise UnknownSensorMessageType, "Unknown Sensor Message Type: #{message_parsed["value_type"]}"
    end
    SensorMessage.Repo.insert(changesett)

    message
  end

  def mapToNumericSensorMessage(numeric_message) do
    {:ok, datetime, _} = DateTime.from_iso8601(numeric_message["timestamp"])

    %SensorMessage.NumericMessage{
      created_at: datetime,
      sensor_id: numeric_message["sensor_id"],
      value: numeric_message["value"] / 1 # division always returns a float
    }
  end

  def mapToStringSensorMessage(string_message) do
    {:ok, datetime, _} = DateTime.from_iso8601(string_message["timestamp"])

    %SensorMessage.StringMessage{
      created_at: datetime,
      sensor_id: string_message["sensor_id"],
      value: string_message["value"]
    }
  end
end

defmodule UnknownSensorMessageType do
  defexception message: "Unknown Sensor Message Type"
end
