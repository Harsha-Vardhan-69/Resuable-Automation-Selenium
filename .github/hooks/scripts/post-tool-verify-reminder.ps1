$raw = [Console]::In.ReadToEnd()

$verifyTargets = @(
    '.java',
    'pom.xml',
    'src/test/resources/testng.xml'
)

$shouldRemind = $false
foreach ($target in $verifyTargets) {
    if ($raw -match [Regex]::Escape($target)) {
        $shouldRemind = $true
        break
    }
}

if ($shouldRemind) {
    $output = @{
        continue = $true
        systemMessage = 'Verification reminder: if this change touched Java, pom.xml, or testng.xml, run the smallest relevant Maven or TestNG command before finishing.'
    } | ConvertTo-Json -Compress
    [Console]::Out.Write($output)
    exit 0
}

$pass = @{
    continue = $true
} | ConvertTo-Json -Compress

[Console]::Out.Write($pass)