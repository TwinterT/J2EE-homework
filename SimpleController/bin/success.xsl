<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<meta http-equiv="Content-Type"
					content="text/html; charset=UTF-8" />
				<title>SUCCESS</title>
			</head>

			<body>
				<textarea row='1' cols='20'><xsl:value-of select="view/body/form/textView/value"/></textarea>

			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>