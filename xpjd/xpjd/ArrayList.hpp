#ifndef   STACK_HPP
#define   STACK_HPP
#include <allocators>
#include <iostream>
#include<iterator>




_OB_SCOPE
template <class _Elem> struct array_traits
{
	static const bool value = false;
};



/*************************************************************************
*
*
*************************************************************************/
template <> struct array_traits < int >
{
	typedef int								value_type;
	typedef int*							pointer;
	typedef const int*				const_pointer;
	typedef int&							reference;
	typedef const int&				const_reference;
	typedef std::size_t				size_type;
	typedef std::ptrdiff_t    difference_type;

};


/*****************************************************************************************************************
*	@brief
* The C++ standard library designed pattern uses the main implementation class has the interface class.
*
******************************************************************************************************************/
template <class _Elem, class _Traits = array_traits<_Elem>, class _Allocator = Allocator<_Elem> > struct ArrayList
{
	
	//most declare interface
	typedef typename _Traits::value_type								value_type;
	typedef typename _Traits::pointer										pointer;
	typedef typename _Traits::const_pointer							const_pointer;
	typedef typename _Traits::reference									reference;
	typedef typename _Traits::const_reference						const_reference;
	typedef typename _Traits::size_type									size_type;
	typedef typename _Traits::difference_type           difference_type;

	ArrayList(size_type const _NSize =  10)
	{
		_Reserved(_NSize);
	}
	 
	ArrayList(const ArrayList& _Other)
	{
		_Other._Alloc = _Alloc;		
	}
private:
	
	void push_back()
	{
    
	};


	void _Reserved(size_type const _NSize)
	{
		  pointer _NewPtr  =  _Alloc.allocate(_NSize);
		 _Counter += _NSize;
		
	}
	//private memebers
	_Allocator _Alloc;
	 

	size_type  _;
};
_END_SCOPE
#endif