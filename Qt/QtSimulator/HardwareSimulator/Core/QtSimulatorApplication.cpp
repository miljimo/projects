/***
*@class QSimulatorApplication
*@construct QSimulatorApplication(int  argc, char *args[], char *evn[])
*@author  Obaro Isreal Johnson <johnson.obaro@hotmail.com>
*@constributors
*-> Obaro Isreal Johnson [2017]
*->
*@brief
* Implementation of the QSimulatorApplication
*/

#include "QtSimulatorApplication.h"
#include "QtConsoleSimulatorApplication.h"
#include <QWidget>
#include <QApplication>

using namespace System::Core;
const unsigned int WIDTH  = 800;
const unsigned int HEIGHT = 800;

QSimulatorApplicationInterface * QSimulatorApplication::instance_ptr = nullptr;

QSimulatorApplication::QSimulatorApplication(int argc, char *argv[])
:qtApp_ptr(new QApplication(argc, argv))
{
	this->widget_ptr = new QWidget(nullptr);
	this->widget_ptr->setWindowTitle("Simulator");
	this->widget_ptr->resize(QSize(WIDTH, HEIGHT));	
}

/*	@brief
*		The member function is used to set the application main window.
*	@param {widget_ptr}
*		The widget object to show as the main ui for the application.
*	@return {void}
*
*/
void  QSimulatorApplication::setWidget(QWidget * const widget_ptr)
{
	_ASSERT(widget_ptr != nullptr);
	this->widget_ptr = widget_ptr;
}

/*
*@brief
* The method is called to start the execution of the application.
*@return {int} the exit code of the application
*/
int QSimulatorApplication::execute()
{
	if (this->widget_ptr != nullptr)
	{
		this->onCreateWidget(this->widget_ptr);
		this->widget_ptr->show();
	}
	return this->qtApp_ptr->exec();
}

/*
*	@brief
*		The function is called immediately when the view is  created.
*   @param{widget} the main system widget
*/
void QSimulatorApplication::onCreateWidget(QWidget *const widget)
{
	Q_UNUSED(widget);

}
/****************************************************************************************************************
* @brief
*  The method create an instance of the application base on the parameter by the command line.
* @param   {argc} the number of argument passed.
* @param   {args} the list of argument values
* @param   {evn} The evironment variables of the systems.
* @return  {QSimulatorApplicationInterface * const} return the current instance if its exists else create a new one;
*******************************************************************************************************************/
QSimulatorApplicationInterface * const QSimulatorApplication::createInstance(int  argc, char *argv[])
{
	if (instance_ptr == nullptr)
	{
		
		for (int i = 1; i < argc; ++i)
		{
			if ((qstrcmp(argv[i], "-no-gui") == 0))
			{
				instance_ptr = new QtConsoleSimulatorApplication(argc, argv);
				break;
			}
			
		}

		if (instance_ptr == nullptr)
		{
			instance_ptr = new QSimulatorApplication(argc, argv);
		}
	}
	return instance_ptr;
}

/**********************************************
*@brief
* Destructor for managing the c++ objects 
* Any point object created by the class should be destroy here 
* Except the object is pass to to the object.
***********************************************/
QSimulatorApplication::~QSimulatorApplication()
{
	delete this->widget_ptr;
	this->widget_ptr = nullptr;
}