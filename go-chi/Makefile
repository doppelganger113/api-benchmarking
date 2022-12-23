SHELL = /bin/bash
PROJECTNAME=api

GOBASE=$(shell pwd)
GOBIN=bin

default:
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -ldflags="-s -w" -o $(GOBIN)/$(PROJECTNAME) .
