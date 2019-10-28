@Grab('com.amazonaws:aws-java-sdk-ecr:1.11.475')
// import required libraries
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.ecr.AmazonECRClientBuilder
import com.amazonaws.services.ecr.model.GetAuthorizationTokenRequest
import groovy.json.JsonSlurper

// Create Sysnet ECR Credentials Variables
def dockerImage  = 'cm'
def awsAccessKey = '***************'
def awsSecretKey = '****************'

// Sysnet ECR client
BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey)
def amazonECR = AmazonECRClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withRegion(Regions.US_EAST_1)
        .build()

def getAuthTokenRequest = new GetAuthorizationTokenRequest()
def authorizationToken = amazonECR.getAuthorizationToken(getAuthTokenRequest)
def authData = authorizationToken.getAuthorizationData()
def rawAuthData = authData.get(0).getAuthorizationToken()
def decodedAuthData = Base64.getDecoder().decode(rawAuthData)

// Authorization data for Sysnet ECR
def username = new String(decodedAuthData).split(":")[0]
def password = new String(decodedAuthData).split(":")[1]
def proxyEndpoint = authData.get(0).getProxyEndpoint()

// GET request to REST API
def addr = "${proxyEndpoint}/v2/${dockerImage}/tags/list"
def authString = "${username}:${password}".getBytes().encodeBase64().toString()
def conn = addr.toURL().openConnection()
conn.setRequestProperty("Authorization", "Basic ${authString}")

// Parsing response
JsonSlurper jsonSlurper = new JsonSlurper()
def output = []

output = jsonSlurper.parseText(conn.content.text).tags
// println output
return output
