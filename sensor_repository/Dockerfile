FROM elixir:1.12.2-alpine

# Install hex package manager
RUN mix local.hex --force

# Create app directory
RUN mkdir /app
WORKDIR /app

# Copy mix file and download dependencies
COPY mix.* ./
RUN mix deps.get
RUN mix local.rebar --force

# Copy the Elixir projects into it
COPY . .


CMD ["/app/entrypoint.sh"]
