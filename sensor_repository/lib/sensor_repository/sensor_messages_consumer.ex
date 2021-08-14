defmodule SensorRepository.SensorMessagesConsumer do
  use Broadway

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
    message
    |> IO.inspect
  end
end
