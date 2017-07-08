#include "QtSimulatorApplication.h"
using namespace System::Core;



int main(int argc, char *argv[])
{
	/*Create the current application to run.
	*/
	auto  app = QSimulatorApplication::createInstance(argc, argv);
	int   ret  =  app->execute();
	delete app;
	return ret;
}
