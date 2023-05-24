defmodule SensorRepository.SensorMessagesConsumer do
  use Broadway
  import SensorMessage.NumericMessage

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
    changesett = mapStringToNumericSensorMessage(message.data) |> changeset
    SensorMessage.Repo.insert(changesett)

    message
  end

  def mapStringToNumericSensorMessage(str) do
    str_parsed = Poison.decode!(str)
    {:ok, datetime, _} = DateTime.from_iso8601(str_parsed["timestamp"])
    
    %SensorMessage.NumericMessage{
      created_at: datetime,
      sensor_id: str_parsed["sensor_id"],
      value: str_parsed["value"] / 1 # division always returns a float
    }
  end
end
