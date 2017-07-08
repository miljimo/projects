#include "QtConsoleSimulatorApplication.h"
#include <QCoreApplication>
#include <QDebug>

using namespace System::Core;

QtConsoleSimulatorApplication::QtConsoleSimulatorApplication(int argc, char * argv[])
:qtApp_ptr(new QCoreApplication(argc, argv))
{
	qDebug() <<"\n --------------------- | Simulator Appplication |-----------------\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << "|                                                                 |\n";
	qDebug() << " -----------------------------------------------------------------\n";
}

/*
*@brief
* The method is called to start the execution of the application.
*@return {int} the exit code of the application
*/
int QtConsoleSimulatorApplication::execute()
{
	return this->qtApp_ptr->exec();
}

/*
 *	@brief
 *   Destructor     
 */
QtConsoleSimulatorApplication::~QtConsoleSimulatorApplication()
{
	delete this->qtApp_ptr;
}