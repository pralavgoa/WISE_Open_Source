package edu.ucla.wise.shared.images;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

public class ImageRenderer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
		PrintWriter out = response.getWriter();

		String imageName = request.getParameter("img");

		if (Strings.isNullOrEmpty(imageName)) {
				out.write("Please provide a name for the image");
				out.close();
				return;
		}

			// return an image
			InputStream imageInputStream = DatabaseConnector
					.getImageFromDatabase(imageName);

			int buffer_size = 2 << 12;
			byte[] byte_buffer = new byte[buffer_size];
			if (imageInputStream != null) {
				response.reset();
				response.setContentType("image/jpg");
				int count = 1;// initializing to a value > 0
				while (count > 0) {
					count = imageInputStream.read(byte_buffer, 0, buffer_size);
					response.getOutputStream().write(byte_buffer, 0,
							buffer_size);
				}
				response.getOutputStream().flush();
			} else {
				System.out.println("Cound not fetch the image");
			}

		} catch (IOException e) {
			System.out.println("Unexpected IO error");
		}

	}

}
