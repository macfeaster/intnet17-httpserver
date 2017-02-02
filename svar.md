**1. Vad är skillnaden mellan GET och POST?**

POST kan ha en HTTP body som används för att skicka in formulärdata, ladda upp filer, etc. GET ska användas för att hämta information, POST för att skicka den. Tekniskt innebär detta att GET har en begränsning i längd (2048 tecken URL), sparas i browserns cacheminne (för att snabba upp hämtning av samma resurs flera gånger), samt går att bokmärka och sparas i browserns historik. POST-requests lagras aldrig i browserns cacheminne, har ingen begränsning i längd och syns inte i browserns historik (och går därför inte att bokmärka.)

**2. Vad är REST?**

REST är inte en teknik utan en arkitektur eller ett sätt att kommunicera med webbtjänster. Tanken bakom arkitekturen är att en webbtjänst erbjuder ett antal "resurser" som går att interagera med genom HTTP-verben för CRUD eller andra API-operationer. REST använder HTTP-protokollet för att utföra operationer.

**3. Vad är de andra HTTP metoderna som används i REST?**

PUT, DELETE samt ibland HEAD och PATCH.