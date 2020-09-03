# Skratchpad

Skratchpad is a simple service that allows a user to perform CRUD operations on a simple database table for notes.

## Build & Run Locally
On Windows:
```
.\start.bat
```

Terminate the program with:
```
.\stop.bat
```
These scripts can be easily ported to a shell script.

## API Documentation

### GET /notes/{id}
Read a note based on note id.

### POST /notes
Create or update a note.

### DELETE /notes/{id}
Delete a note based on note id.

