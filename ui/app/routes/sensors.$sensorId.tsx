import { useParams } from "@remix-run/react";
import { useLoaderData } from "@remix-run/react";

export const loader = async ({ params }: { params: { sensorId: string } }) => {
  const { sensorId } = params;
  const response = await fetch(`http://localhost:8080/${sensorId}/values`);
  if (!response.ok) {
    throw new Response("Failed to fetch data", { status: response.status });
  }
  const data = await response.json();
  return data;
};


export default function Contact() {
  const { sensorId } = useParams();
  const data = useLoaderData<typeof loader>();

  return (
    <div>
      <h1>Sensor {sensorId}</h1>
      <table border="1" cellPadding="10" cellSpacing="0">
        <thead>
          <tr>
            <th>Timestamp</th>
            <th>Value</th>
            <th>Value Type</th>
          </tr>
        </thead>
        <tbody>
          {data.values.map((value, index) => (
            <tr key={index}>
              <td>{new Date(value.timestamp).toLocaleString()}</td>
              <td>{value.value}</td>
              <td>{value.value_type}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
