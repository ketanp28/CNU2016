import understand

db = understand.open("/var/app/testdb/test_sonic.udb")

for method in db.ents("Methods"):
	if('run'==method.name()):
		runCalledbyList = method.refs('CallBy')
			for classCalled in runCalledbyList:
				defEnt = classCalled.ent().ref("DefineIn").ent()
				for item in runCalled.ent().refs('Java Extend Couple'):

					if("Thread" in item.ent().name()):
						print('Method: ', method.name(), 'is a thread in class ', defEnt.name(),
							'extended from ', item.file(),'Did you mean to call start() instead?')

					if("Runnable" in item.ent().name()):
						print('Method: ', method.name(), 'is a runnable in class ', defEnt.name(),
							'extended from ', item.file(),'Did you mean to call start() instead?')


