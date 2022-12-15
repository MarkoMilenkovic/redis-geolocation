# redis-geolocation
Using redis for geospatial queries.

Endpoint to add point:

 POST location/{id}
{
"id": "BARBER2",
"lng": 21.5187516,
"lat": -0.0814374
}

Endpoint to delete point:

DELETE location/{id}


Endpoint to query by location:

GET location/nearby?lng=51.4595574&lat=0.24949&km=10000

Response will contain coordinates and distance, example:
{
"id": "1",
"lat": 51.51875227689743,
"lng": -0.08143678876782445,
"distance": 37.392
}