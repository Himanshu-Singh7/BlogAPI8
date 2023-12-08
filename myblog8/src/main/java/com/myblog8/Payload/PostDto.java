package com.myblog8.Payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	 private Long id;
	 
	 @NotEmpty
	 @Size(min = 2,message = "Post title sould be at least 2 charactor")
	 private String title;
	 @NotEmpty
	 @Size(min = 4,message = "Post description sould be at least 4 charactor")
	 private String description;
	 
	 @NotEmpty
	 @Size(min = 5,message = "Post content sould be at least 5 charactor")
	 private String content; 

}
