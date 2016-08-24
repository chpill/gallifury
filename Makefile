# Illustrates some things you might want to do on the command line

# Create a user named bob using the HTTP API
# Note that the data is encoded in transit+json;verbose
user-bob:
	echo '{"~:user/email":"bob@example.com", "~:user/name":"bob"}' |\
	http -v :9191/users Content-Type:"application/transit+json;verbose"

.PHONY: user-bob
