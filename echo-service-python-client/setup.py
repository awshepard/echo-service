#!/usr/bin/env python

from setuptools import setup, find_packages

requires = [
    'frozendict',
    'requests',
    'upside-core',
    'upside-model',
]

setup_options = dict(
    name='echo_service_client',
    version='[SCM_VERSION]',
    description='Echo Service rest client.',
    long_description='Long description',
    author='GetUpside',
    url='https://github.com/upside-services/echo_service',
    scripts=[],
    packages=find_packages(exclude=['tests*']),
    install_requires=requires
)

setup(**setup_options)
