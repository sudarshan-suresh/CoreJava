package annotation;

import java.lang.annotation.Documented;

/** Author: John Doe
 Date: 3/17/2002
 Current revision: 6
 Last modified: 4/12/2004
 By: Jane Doe
 Reviewers: Alice, Bill, Cindy */

// class code goes here
@Documented
public @interface ClassPreamble {
	String author();
	String date();
	int currentRevision() default 1;
	String lastModified() default "n/a";
	String lastModifiedBy() default "n/a";
	String[] reviewers();
	
}
