/************************************************************************
*@brief
*	The module contains the main application interface.
*	
*************************************************************************/
#ifndef QSIMULATOR_APPLICATION_INTERFACE_H
#define QSIMULATOR_APPLICATION_INTERFACE_H

namespace System
{
	namespace Core
	{
		class QSimulatorApplicationInterface
		{
		public:
			/*
			*@brief 
			* The method is called to start the execution of the application.
			*@return {int} the exit code of the application
			*/
			virtual int execute() = 0;
		};
	}
}

#endif