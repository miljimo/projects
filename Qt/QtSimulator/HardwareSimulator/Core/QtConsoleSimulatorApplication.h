/************************************************************************
*@brief
*	The console support part of the application
*
*************************************************************************/
#ifndef Q_SIMULATOR_APPLICATION_CONSOLE_H
#define Q_SIMULATOR_APPLICATION_CONSOLE_H
#include "QSimulatorApplicationInterface.h"

class QCoreApplication;
namespace System
{
	namespace Core
	{
		class QtConsoleSimulatorApplication :public QSimulatorApplicationInterface
		{
		private:
			QCoreApplication *qtApp_ptr;
		public:
			QtConsoleSimulatorApplication(int argc, char * argv[]);
			/*
			*@brief
			* The method is called to start the execution of the application.
			*@return {int} the exit code of the application
			*/
			virtual int execute()override;

			virtual ~QtConsoleSimulatorApplication();
		};
	}
}
#endif