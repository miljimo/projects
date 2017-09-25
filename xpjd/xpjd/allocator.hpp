#ifndef STACK_ALLOCATOR_H
#define STACK_ALLOCATOR_H
#include "typedefs.hpp"
#include <type_traits>

_OB_SCOPE
/***********************************************************************************************
*	@brief
*		The allocator class  for practices
*
*
************************************************************************************************/
template <class _Elem > struct    Allocator
{
	typedef _Elem						       value_type;
	typedef _Elem*								 pointer;
	typedef const value_type *		 const_pointer;
	typedef value_type &					 reference;
	typedef const _Elem&           const_reference;
	typedef std::ptrdiff_t         difference_type;
	typedef std::size_t            size_type;


	/**************************************************************************
	*	@constructor
	*		The default constructor
	**************************************************************************/
	Allocator() _NoExceptions
	{
		
	}
	/**************************************************************************
	*	@constructor
	*		The default copy constructor
	**************************************************************************/
	Allocator(const Allocator & _Allocator) _NoExceptions
	:Allocator<_Elem>()
	{
		
	}
	/**************************************************************************
	*	@constructor
	*		The copy with different value  constructor
	**************************************************************************/
	template<class _UElem > Allocator(const Allocator<_UElem> & Other) _NoExceptions
		:Allocator()
	{

	}
	/**************************************************************************
	*	@brief
	*		Assignment operator 
	**************************************************************************/
	template<class  _UElem> Allocator & operator= (const Allocator<_UElem> &) _NoExceptions
	{
		return  *this;
	}


	//The rebind member allows a container to construct an allocator for some arbitrary data type
	template<typename  U> struct rebind
	{
		typedef Allocator<U> other;
	};

	//Public Member Functions
	//This function and the following function are used to convert references to pointers.
	inline pointer       address(reference r)       const { return static_cast<pointer>(&r); }
	inline const_pointer address(const_reference r) const { return const_cast<const_pointer>(&r); }

	/**************************************************************************
	*	@brief
	*		The function is used to allocated  memory size for n number of _Hint
	*	@param {n} the number of item to allocated
	* @param {_Hint} an hint how item, do not know what this is for;
	* @return {pointer} return an empty pointer to the allocated address.
	***************************************************************************/
	pointer      allocate(size_t n, typename Allocator<void*>::const_pointer _Hint = nullptr)
	{
		return reinterpret_cast<pointer>(::operator new(n * sizeof(_Elem)));
	}
	/**************************************************************************
	*	@brief
	*		Deallocate the allocated memory
	*	@param {_Obj} The object to deallocated.
	* @param {_nSize} 	number of objects earlier passed to allocate
	***************************************************************************/
	void         deallocate(pointer _Obj, size_type _nSize = 0)
	{
		if (_Obj != nullptr)
			::operator delete(_Obj);	
	}

	void reallocate(pointer _Ptr, size_t _NSize = 1){
		if (_Ptr)
		{
			deallocate(_Ptr);
		}
		_Ptr = allocate(_NSize);
	}

	template <class _Type> void construct(pointer _Ptr) _NoExceptions
	{
		new(_Ptr)_Elem();
	}

	/**************************************************************************
	*	@brief
	*   The function construct the  object
	*
	* @return {size_type} return the max size to allocated.
	**************************************************************************/

	template <class _Type> void construct(pointer _Ptr, const _Type&  _Val) _NoExceptions
	{
		new(_Ptr)_Elem(std::forward<_Type>(_Val));	
	}
	/**************************************************************************
	*	@brief
	*   Variable function to auto contruct an object arbitrary number of values
	*
	* @return {size_type} return the max size to allocated.
	**************************************************************************/
	template<class ... _Types> void construct(pointer _Ptr, _Types&&... _Args)
	{
		::new((void*)_Ptr)_Elem(_STD_ forward<_Types>(_Args)...);
	}


	void destroy(pointer _Ptr) _NoExceptions
	{
		if (_Ptr)
		{			
			_Ptr->~_Elem();
		}
	}

	/**************************************************************************
	*	@brief
	*
	* @return {size_type} return the max size to allocated.
	**************************************************************************/
	typename size_t max_size() const {
		return  (std::size_t(-1) / sizeof(_Elem));
	}

	/**************************************************************************
	*	@brief
	*   Destroy all the allocated objects.
	* @return {size_type} return the max size to allocated.
	**************************************************************************/
	virtual ~Allocator()_NoExceptions
	{
		
	}
	private:
		
};

_END_SCOPE
#endif