(progn
 (defun print java:com.googlecode.linkedlisp.functions.io.Print)
 (defun write-line java:com.googlecode.linkedlisp.functions.io.WriteLine)

 (defun if  java:com.googlecode.linkedlisp.functions.control.If)
 (defun loop java:com.googlecode.linkedlisp.functions.control.Loop)

 (defun lambda  java:com.googlecode.linkedlisp.functions.Lambda)
 (defun let  java:com.googlecode.linkedlisp.functions.Let)
 (defun setf java:com.googlecode.linkedlisp.functions.Setf)
 (defun get  java:com.googlecode.linkedlisp.functions.Get)
 (defun set  java:com.googlecode.linkedlisp.functions.Set)
 (defun new  java:com.googlecode.linkedlisp.functions.New)
 (defun unset  java:com.googlecode.linkedlisp.functions.UnSet)

 (defun QUOTE  java:com.googlecode.linkedlisp.functions.list.Quote)
 (defun cons  java:com.googlecode.linkedlisp.functions.list.Cons)
 (defun cdr  java:com.googlecode.linkedlisp.functions.list.Cdr)
 (defun car  java:com.googlecode.linkedlisp.functions.list.Car)
 (defun append  java:com.googlecode.linkedlisp.functions.list.Cons)
 (defun rest  java:com.googlecode.linkedlisp.functions.list.Cdr)
 (defun first  java:com.googlecode.linkedlisp.functions.list.Car)
 (defun last  java:com.googlecode.linkedlisp.functions.list.Last)
 (defun seq  java:com.googlecode.linkedlisp.functions.list.Seq)

 (defun concatenate  java:com.googlecode.linkedlisp.functions.text.Concatenate)
 (defun split  java:com.googlecode.linkedlisp.functions.text.Split)
 (defun replace  java:com.googlecode.linkedlisp.functions.text.Replace)
 (defun regex  java:com.googlecode.linkedlisp.functions.text.Replace)

 (defun find  java:com.googlecode.linkedlisp.functions.semantic.Find)
 (defun load  java:com.googlecode.linkedlisp.functions.semantic.Load)
 (defun prefix  java:com.googlecode.linkedlisp.functions.semantic.Prefix)
 (defun builtin  java:com.googlecode.linkedlisp.functions.semantic.Builtin)
 (defun rule  java:com.googlecode.linkedlisp.functions.semantic.Rule)
 (defun bind  java:com.googlecode.linkedlisp.functions.semantic.Bind)
 (defun model.mem  java:com.googlecode.linkedlisp.functions.semantic.MakeModel)
 (defun model.tdb  java:com.googlecode.linkedlisp.functions.semantic.MakeTDBModel)
 (defun model.let  java:com.googlecode.linkedlisp.functions.semantic.ModelLet)

 (defun + java:com.googlecode.linkedlisp.functions.math.Plus)
 (defun - java:com.googlecode.linkedlisp.functions.math.Minus)
 (defun / java:com.googlecode.linkedlisp.functions.math.Divide)
 (defun * java:com.googlecode.linkedlisp.functions.math.Multiply)
 (defun % java:com.googlecode.linkedlisp.functions.math.Mod)
 
 (defun and java:com.googlecode.linkedlisp.functions.math.And)
 (defun or  java:com.googlecode.linkedlisp.functions.math.Or)
 (defun not java:com.googlecode.linkedlisp.functions.math.Not)

 (defun = java:com.googlecode.linkedlisp.functions.math.Equal)

)
