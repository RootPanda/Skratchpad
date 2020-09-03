# Skratchpad

Skratchpad is a simple service that allows a user to perform CRUD operations on a simple database table for notes.

## Building
On Windows:
```
docker build -t skratchpad:latest .
.\start.bat
```

## API Documentation

### GET /notes/{id}
Read a note based on note id.

### POST /notes
Create or update a note.

### DELETE /notes/{id}
Delete a note based on note id.

