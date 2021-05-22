## Dev Environment
### Required CLI tools
- `readability`: install with `npm i readability-cli@2.3.0 --global`
- `ebook-convert`: install from https://calibre-ebook.com/download. Tested on v5.18.0

### Docker compose
Start the dependencies declared in docker-compose.yml by running `docker compose up -d`
Then run the application inside your preferred IDE

### Run configuration
Run with the system property variables set to below values by passing in the VM arguments with `-D{key}={value}`

|key|value|description|
|---|---|---|
|NOVEL_SERVICE_CONFIG_FILE|<path-to-repo>/novel-worker-config.yml|Config file location|
|NOVEL_FOLDER_KEY|Any valid folder path|A folder to save novels|
