Echo Service Python Client
==========================

One Time Setup
--------------
* `brew install python`
* `pip install virtualenv`


Install Dependencies
--------------------
* `cd echo-service-python-client`
* `virtualenv env`
* `source env/bin/activate`
* `pip install .`

Using the Client
----------------
```
from upside_core.services import create_private_service_with_retries
from echo_service.client import EchoService

echo_client = create_private_service_with_retries('prod', EchoService)

response, code = echo_client.get_service_unavailable_50()
```

