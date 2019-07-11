import requests
from frozendict import frozendict
from upside_core.request_util import json_or_error, json_none_or_error


class EchoService(object):
    """
    Hand rolled client for the user service.
    """
    name = 'echo'

    def __init__(self, auth, base_url, session_factory=None):
        self.base_url = base_url
        self.auth = auth
        is_new_session = False
        if not session_factory:
            is_new_session = True
            session_factory = requests.session
        self.session = session_factory()

        if is_new_session:
            self.session.mount('http://', requests.adapters.HTTPAdapter(max_retries=5))
            self.session.mount('https://', requests.adapters.HTTPAdapter(max_retries=5))

    def get_env(self):
        response = self.session.get(url='{baseUrl}/echo/env'.format(baseUrl=self.base_url),
                                    auth=self.auth)

        return json_or_error(response)

    def get_service_unavailable_50(self):
        response = self.session.get(url='{baseUrl}/echo/serviceUnavailable50'.format(baseUrl=self.base_url),
                                    auth=self.auth)

        return response.text, response.status_code
