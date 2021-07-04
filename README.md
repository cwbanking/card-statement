# card-statement
Microsserviço de extrato de cartão de crédito

# build image using maven jib plugin
mvn compile jib:build

# environment variables
SPRING_DATA_MONGODB_URI=mongodb+srv://<<mongodb-username>>:<<mongodb-password>>@<<mongodb-host>>/<<mongodb-database>>?retryWrites=true&w=majority