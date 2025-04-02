$url = "http://localhost:8080/api/transactions"
$headers = @{
    "Content-Type" = "application/json"
}

# Convert FORACID to base64 bytes
$foracid = "123456789"
$foracidBytes = [System.Text.Encoding]::UTF8.GetBytes($foracid)
$foracidBase64 = [Convert]::ToBase64String($foracidBytes)

# Convert other fields to base64
$acctName = "Test Account"
$acctNameBytes = [System.Text.Encoding]::UTF8.GetBytes($acctName)
$acctNameBase64 = [Convert]::ToBase64String($acctNameBytes)

$tranId = "TXN123"
$tranIdBytes = [System.Text.Encoding]::UTF8.GetBytes($tranId)
$tranIdBase64 = [Convert]::ToBase64String($tranIdBytes)

$tranType = "CR"
$tranTypeBytes = [System.Text.Encoding]::UTF8.GetBytes($tranType)
$tranTypeBase64 = [Convert]::ToBase64String($tranTypeBytes)

$body = @{
    "schema" = "com.plutus.kotak.commonlibs.avro.PlutusFinacleData"
    "fields" = @(
        @{
            "name" = "FORACID"
            "value" = $foracidBase64
            "type" = "bytes"
        },
        @{
            "name" = "ACCT_NAME"
            "value" = $acctNameBase64
            "type" = "bytes"
        },
        @{
            "name" = "LAST_TRAN_DATE_CR"
            "value" = "2024-04-01T10:00:00"
            "type" = "string"
        },
        @{
            "name" = "TRAN_DATE"
            "value" = "2024-04-01T10:00:00"
            "type" = "string"
        },
        @{
            "name" = "TRAN_ID"
            "value" = $tranIdBase64
            "type" = "bytes"
        },
        @{
            "name" = "PART_TRAN_SRL_NUM"
            "value" = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("1"))
            "type" = "bytes"
        },
        @{
            "name" = "DEL_FLG"
            "value" = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("N"))
            "type" = "bytes"
        },
        @{
            "name" = "TRAN_TYPE"
            "value" = $tranTypeBase64
            "type" = "bytes"
        },
        @{
            "name" = "TRAN_SUB_TYPE"
            "value" = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("NORMAL"))
            "type" = "bytes"
        },
        @{
            "name" = "VALUE_DATE"
            "value" = "2024-04-01T10:00:00"
            "type" = "string"
        },
        @{
            "name" = "TRAN_AMT"
            "value" = 1000.50
            "type" = "double"
        },
        @{
            "name" = "TRAN_PARTICULAR"
            "value" = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("Test Transaction"))
            "type" = "bytes"
        },
        @{
            "name" = "ENTRY_DATE"
            "value" = "2024-04-01T10:00:00"
            "type" = "string"
        },
        @{
            "name" = "PSTD_DATE"
            "value" = "2024-04-01T10:00:00"
            "type" = "string"
        }
    )
}

$bodyJson = $body | ConvertTo-Json -Depth 10

# Send POST request
try {
    $response = Invoke-RestMethod -Uri $url -Method Post -Headers $headers -Body $bodyJson
    Write-Host "Response:"
    $response | ConvertTo-Json
} catch {
    Write-Host "Error occurred:"
    Write-Host $_.Exception.Message
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.BaseStream.Position = 0
        $reader.DiscardBufferedData()
        $responseBody = $reader.ReadToEnd()
        Write-Host $responseBody
    }
}